using Confluent.Kafka;
using System;
using System.Threading;
using Microsoft.Extensions.Configuration;

class Producer
{
    static void Main(string[] args)
    {
        if (args.Length != 1)
        {
            Console.WriteLine("Please provide the configuration file path as a command line argument");
        }

        IConfiguration configuration = new ConfigurationBuilder()
            .AddIniFile(args[0])
            .Build();

        const string TOPIC = "purchases";

        string[] users = {"thomas"};
        string[] items = {"book", "alarm clock", "t-shirts", "gift card", "batteries"};

        using (var producer = new ProducerBuilder<string, string>(
            configuration.AsEnumerable()).Build())
            {
                var numProduced = 0;
                Random rnd = new Random();
                const int NUMMESSAGES = 10;
                for(int i = 0; i < NUMMESSAGES; i++)
                {
                    var user = users[rnd.Next(users.Length)];
                    // var item = items[rnd.Next(items.Length)];
                    var item = $"Dette er melding nr: {i}";

                    producer.Produce(TOPIC, new Message<string, string> {Key = user, Value = item},
                        (deliveryReport) => 
                        {
                            if(deliveryReport.Error.Code != ErrorCode.NoError)
                            {
                                Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}");
                            }
                            else
                            {
                                Console.WriteLine($"Produced event to topic {TOPIC}: key = {user,-10} value = {item}");
                                numProduced += 1;
                            }
                        });
                    Thread.Sleep(2000);
                }

                producer.Flush(TimeSpan.FromSeconds(10));
                Console.WriteLine($"{numProduced} messages were produced to topic {TOPIC}");
            }
    }
}