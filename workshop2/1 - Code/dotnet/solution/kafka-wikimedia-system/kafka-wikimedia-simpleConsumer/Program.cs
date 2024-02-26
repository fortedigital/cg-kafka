using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;

var consumerConfig = new ConsumerConfig
{
    BootstrapServers = "localhost:19092",
    GroupId = "Thomas-1",
    AutoOffsetReset = AutoOffsetReset.Earliest
};

var schemaRegCon = new SchemaRegistryConfig
{
    Url = "http://localhost:8081/",
};

const string TOPIC = "RecentChanges";


CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true;
    cts.Cancel();
};

using (var consumer = new ConsumerBuilder<string, object>(consumerConfig).SetValueDeserializer(new JsonDeserializer<object>().AsSyncOverAsync()).Build())
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