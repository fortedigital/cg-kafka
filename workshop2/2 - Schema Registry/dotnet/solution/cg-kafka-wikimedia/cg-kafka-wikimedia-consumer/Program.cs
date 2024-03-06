using cg_kafka_wikimedia_consumer;
using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;

var consumerConfig = new ConsumerConfig
{
    BootstrapServers = "localhost:19092",
    GroupId = "user-1",
    AutoOffsetReset = AutoOffsetReset.Earliest
};

//var schemaRegCon = new SchemaRegistryConfig
//{
//    Url = "http://localhost:8081/",
//};

const string TOPIC = "RecentChanges";


CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true;
    cts.Cancel();
};

//using (var schemaRegistry = new CachedSchemaRegistryClient(schemaRegCon))
using (var consumer = new ConsumerBuilder<string, RecentChangeData>(consumerConfig).SetValueDeserializer(new JsonDeserializer<RecentChangeData>().AsSyncOverAsync()).Build())
{
    consumer.Subscribe(TOPIC);

    try
    {
        while (true)
        {
            var cr = consumer.Consume(cts.Token);
            Console.WriteLine(cr.Value.ToString());
        }
    }
    catch (OperationCanceledException)
    {
        // Ctrl-c was pressed
    }
    finally
    {
        consumer.Close();
        consumer.Dispose();
    }
}