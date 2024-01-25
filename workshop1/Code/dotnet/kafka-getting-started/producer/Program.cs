using Confluent.Kafka;
using Microsoft.Extensions.Configuration;

IConfiguration configuration = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json", false, true)
        .Build();

const string TOPIC = "purchases";

string[] users = { "thomas" };
string[] items = { "book", "alarm clock", "t-shirts", "gift card", "batteries" };

using (var producer = new ProducerBuilder<string, string>(
    configuration.AsEnumerable()).Build())
{
    var numProduced = 0;
    Random rnd = new Random();
    const int NUMMESSAGES = 10;
    for (int i = 0; i < NUMMESSAGES; i++)
    {
        var user = users[rnd.Next(users.Length)];
        var item = items[rnd.Next(items.Length)];
        //var item = $"{users} - Dette er melding nr: {i}";

        producer.Produce(TOPIC, new Message<string, string> { Key = user, Value = item },
            (deliveryReport) =>
            {
                if (deliveryReport.Error.Code != ErrorCode.NoError)
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
    producer.Dispose();
    Console.WriteLine($"{numProduced} messages were produced to topic {TOPIC}");

}