using Confluent.Kafka;

const string TOPIC = "purchases";

string[] users = { "Daud", "Thomas", "Ola", "Erol", "Ludvig" };
string[] items = { "Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug" };

var producerConfig = new ProducerConfig
{
    BootstrapServers = "localhost:19092"
};

using (var producer = new ProducerBuilder<string, string>
    (producerConfig).Build())
{
    Random rnd = new Random();
    const int NUMMESSAGES = 15;

    for(int i = 0; i < NUMMESSAGES; i++)
    {
        var user = users[rnd.Next(users.Length)];
        var item = items[rnd.Next(items.Length)];

        producer.Produce(TOPIC, new Message<string, string> { Key = user, Value = item },
            (deliveryReport) =>
            {
                if (deliveryReport.Error.Code != ErrorCode.NoError)
                {
                    Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}");
                }
                else
                {
                    Console.WriteLine($"Produced event to topic {TOPIC}: key = {user,-10} value = {item,-10} partition = {deliveryReport.Partition,-10} offset = {deliveryReport.Offset,-10}");
                }
            });
        Thread.Sleep(500);
    }

    producer.Flush(TimeSpan.FromSeconds(10));
    producer.Dispose();
    Console.WriteLine("Closing the producer");
}