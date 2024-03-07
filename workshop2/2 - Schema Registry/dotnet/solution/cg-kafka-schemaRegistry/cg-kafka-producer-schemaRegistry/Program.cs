using cg_kafka_producer_schemaRegistry;
using Confluent.Kafka;
using Confluent.Kafka.SyncOverAsync;
using Confluent.SchemaRegistry;
using Confluent.SchemaRegistry.Serdes;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;

IConfiguration configuration = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json", false, true)
        .Build();

const string TOPIC = "purchases";

Random rnd = new Random();

string[] users = { "Daud", "Thomas", "Ola", "Erol", "Ludvig" };

Purchase[] items =
{
    new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Book",
        Price = 250,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },
    new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Alarm clock",
        Price = 375,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "T-shirts",
        Price = 285,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Gift card",
        Price = 30,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Batteries",
        Price = 125,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Soda",
        Price = 35,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },new Purchase
    {
        Id = Guid.NewGuid(),
        Name = "Coffee mug",
        Price = 186,
        Quantity = rnd.Next(0,25),
        TransactionId = Guid.NewGuid(),
    },
};

SchemaRegistryConfig shemaRegConfig = new()
{
    Url = "http://localhost:8081/",
};

using (var schemaRegistry = new CachedSchemaRegistryClient(shemaRegConfig))
using (var producer = new ProducerBuilder<string, Purchase>(configuration.AsEnumerable())
    .SetValueSerializer(new JsonSerializer<Purchase>(schemaRegistry).AsSyncOverAsync())
    .Build())
{
    const int NUMMESSAGES = 15;
    for (int i = 0; i < NUMMESSAGES; i++)
    {
        var user = users[rnd.Next(users.Length)];
        var item = items[rnd.Next(items.Length)];


        producer.Produce(TOPIC, new Message<string, Purchase> { Key = user, Value = item }, (deliveryReport) =>
        {
            if (deliveryReport.Error.Code != ErrorCode.NoError)
            {
                Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}");
            }
            else
            {
                Console.WriteLine($"Produced event to topic {TOPIC}: key = {user,-10} value = {item}");
            }
        });
        Thread.Sleep(2000);
    }

    producer.Flush(TimeSpan.FromSeconds(10));
    producer.Dispose();
}