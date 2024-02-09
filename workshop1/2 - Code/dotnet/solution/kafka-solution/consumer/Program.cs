using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;

const string TOPIC = "purchases";

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


CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true;
    cts.Cancel();
};

using (var consumer = new ConsumerBuilder<string, Purchase>(consumerConfig).SetValueDeserializer(new JsonDeserializer<Purchase>().AsSyncOverAsync()).Build())
{
    consumer.Subscribe(TOPIC);

    try
    {
        while (true)
        {
            var cr = consumer.Consume(cts.Token);
            Console.WriteLine($"Consumed event from topic {TOPIC}: key = {cr.Message.Key,-5} value = {cr.Message.Value.Id}, {cr.Message.Value.Name}, {cr.Message.Value.Price}, {cr.Message.Value.Quantity}");
        }
    }
    catch(OperationCanceledException) 
    {
        // Ctrl-c was pressed
    }
    finally
    {
        consumer.Close();
        consumer.Dispose();
    }
}