using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;
using static Confluent.Kafka.ConfigPropertyNames;

namespace kafka_wikimedia_stream;
public class KafkaProducer : IKafkaProducer
{
    const string TOPIC = "RecentChanges";

    private readonly SchemaRegistryConfig _schemaRegConfig;
    private readonly ProducerConfig _producerConfig;

    private readonly CachedSchemaRegistryClient _schemaReg;
    private readonly IProducer<string, RecentChangeData> _producer;

    public KafkaProducer()
    {
        _schemaRegConfig = new SchemaRegistryConfig()
        {
            Url = "http://localhost:8081/",
        };

        _producerConfig = new ProducerConfig()
        {
            BootstrapServers = "localhost:19092"
        };

        _schemaReg = new CachedSchemaRegistryClient(_schemaRegConfig);
        _producer = new ProducerBuilder<string, RecentChangeData>(_producerConfig)
            .SetValueSerializer(new JsonSerializer<RecentChangeData>(_schemaReg).AsSyncOverAsync())
            .Build();
    }

    public async Task SendMessage(RecentChangeData recentChange)
    {

        _producer.Produce(TOPIC, new Message<string, RecentChangeData> { Key = recentChange.type, Value = recentChange },
            (deliveryReport) =>
            {
                if (deliveryReport.Error.Code != ErrorCode.NoError)
                {
                    Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}");
                }
                else
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine($"Produced event to topic {TOPIC}: key = {recentChange.type,-10} value = {recentChange.ToString()}");
                    Console.ForegroundColor = ConsoleColor.White;

                }
            });
    }

    public void Dispose()
    {
        _producer.Flush(TimeSpan.FromSeconds(10));
        _producer.Dispose();
    }
}
