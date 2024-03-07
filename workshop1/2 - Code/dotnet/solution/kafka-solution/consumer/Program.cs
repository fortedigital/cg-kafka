using Confluent.Kafka;

const string TOPIC = "purchases";

var consumerConfig = new ConsumerConfig
{
    BootstrapServers = "localhost:19092",
    GroupId = "Thomas-1",
    AutoOffsetReset = AutoOffsetReset.Earliest
};

CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true;
    cts.Cancel();
};

using (var consumer = new ConsumerBuilder<string, string>(consumerConfig).Build())
{
    consumer.Subscribe(TOPIC);

    try
    {
        while (true)
        {
            var cr = consumer.Consume(cts.Token);
            Console.WriteLine($"Consumed event from topic {TOPIC}: key = {cr.Message.Key,-5} value = {cr.Message.Value}");
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