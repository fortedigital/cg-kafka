using Confluent.Kafka;
using Microsoft.Extensions.Configuration;

// bind the properties for the consumer
IConfiguration configuration = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json", false, true)
        .Build();

const string TOPIC = "messages";

// Create Kafka Producer config
var producerConfig = new ProducerConfig(new Dictionary<string, string>(configuration.AsEnumerable()));

// Create Kafka Consumer config
configuration["group.id"] = "Thomas.nr1";
var consumerConfig = new ConsumerConfig(new Dictionary<string, string>(configuration.AsEnumerable()));

// Creat the Kafka producer
using (var producer = new ProducerBuilder<string, string>(producerConfig).Build()) 
{
    // Create the Kafka consumer
    using (var consumer = new ConsumerBuilder<string, string>(consumerConfig).Build())
    {
        // Handle cancelation
        CancellationTokenSource cts = new CancellationTokenSource();
        Console.CancelKeyPress += (_, e) =>
        {
            e.Cancel = true; // prevent the process from terminating.
            cts.Cancel();
        };

        // Subscribe to topic
        consumer.Subscribe(TOPIC);

        // Create consume thread that read messages
        var consumerThread = new Thread(() =>
        {
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
        });
        consumerThread.Start();

        // Create produce thread
        Console.WriteLine("Type 'exit' to stop the application");
        while (true)
        {
            Console.WriteLine("Send a message");
            var input = Console.ReadLine();

            // Check for exit flag
            if(input.ToLower() == "exit")
            {
                break;
            }

            var message = new Message<string, string>
            {
                Key = "Thomas",
                Value = input
            };
            producer.Produce(TOPIC, message, (deliveryReport) =>
            {
                if (deliveryReport.Error.Code != ErrorCode.NoError)
                {
                    Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}");
                }
                else
                {
                    Console.WriteLine($"Produced event to topic {TOPIC}: key = {message.Key,-10} value = {input}");
                }
            });
            
        }

        consumerThread.Join();
    }

    producer.Flush(TimeSpan.FromSeconds(10));
    producer.Dispose();
}