using Confluent.Kafka;
using Microsoft.Extensions.Configuration;

// bind the properties for the consumer
IConfiguration configuration = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json", false, true)
        .Build();

// define our topic
const string TOPIC = "purchases";

// Handle cancelation
CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true; // prevent the process from terminating.
    cts.Cancel();
};

// Set up the consumer using the consumer builder. The consumerbuilder takes the configurations and binds the values to the consumer.
using (var consumer = new ConsumerBuilder<string, string>(
        configuration.AsEnumerable()).Build())
    {
        // have the consumer subscribe to our topic
        consumer.Subscribe(TOPIC);

        // To gracefully handle possible errors or closing the connection we wrap the listening loop in a trycatch
        try
        {
            while (true)
            {
                // consume and display the message
                var cr = consumer.Consume(cts.Token);
                Console.WriteLine($"Consumed event from topic {TOPIC}: key = {cr.Message.Key,-10} value = {cr.Message.Value}");
            }
        }
        catch (OperationCanceledException)
        {
            // Ctrl-C was pressed.
        }
        finally
        {
            // gracefully close the connection then dispose of the object.
            consumer.Close();
            consumer.Dispose();
        }
    }
