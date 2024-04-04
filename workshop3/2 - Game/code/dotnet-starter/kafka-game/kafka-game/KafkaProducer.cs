using Confluent.Kafka;
using kafka_game.models;
using System.Text.Json;

namespace kafka_game;

public class KafkaProducer : IKafkaProducer
{
    const string TOPIC = "kafka-game";

    private readonly IProducer<string, string> _producer;

    public KafkaProducer()
    {
        var producerConfig = new ProducerConfig()
        {
            BootstrapServers = "kafka.daud.dev:19092"
        };

        _producer = new ProducerBuilder<string, string>(producerConfig).Build();
    }

    public async Task SendMessage(Answers answer)
    {
        string answerString = JsonSerializer.Serialize(answer);

        _producer.Produce(TOPIC, new Message<string, string>
        {
            Key = "",
            Value = answerString,
        },
        (deliveryReport) =>
        {
            if (deliveryReport.Error.Code != ErrorCode.NoError)
            {
                Console.WriteLine($"Failed to deliver message: {deliveryReport.Error.Reason}!");
            }
        });
    }

    public void Dispose()
    {
        _producer.Flush(TimeSpan.FromSeconds(10));
        _producer.Dispose();
    }
}
