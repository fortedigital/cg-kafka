using Confluent.Kafka;
using kafka_game.models;
using System.Text.Json;
using static Confluent.Kafka.ConfigPropertyNames;

namespace kafka_game;

public class GameApp : IGameApp
{
    const string TOPIC = "kafka-game";

    private readonly IConsumer<string, string> _consumer;
    private readonly IKafkaProducer _producer;

    public GameApp(IKafkaProducer producer)
    {
        var consumerConfig = new ConsumerConfig
        {
            BootstrapServers = "kafka.daud.dev:19092",
            GroupId = "dotenetTest_2",
            AutoOffsetReset = AutoOffsetReset.Earliest
        };

        _consumer = new ConsumerBuilder<string, string>(consumerConfig).Build();
        _producer = producer;
    }

    public async Task Start()
    {
        CancellationTokenSource cts = new CancellationTokenSource();
        Console.CancelKeyPress += (_, e) =>
        {
            e.Cancel = true;
            cts.Cancel();
        };
        bool flag = true;
        _consumer.Subscribe(TOPIC);

        while (flag)
        {
            try
            {
                while (true)
                {
                    var cr = _consumer.Consume(cts.Token);
                    var message = JsonSerializer.Deserialize<Questions>(cr.Message.Value);

                    // Handle the question, generate an answer and send response.
                    var answer = new Answers()
                    {
                        type = "ANSWER",
                        questionId = ,
                        category = ,
                        teamName = ,
                        answer = ,
                        messageId = Guid.NewGuid(),
                        created = DateTime.Now,
                    };
                    await _producer.SendMessage(answer);
                }
            }
            catch (OperationCanceledException oce)
            {
                Console.WriteLine($"ctrl-c pressed. message: {oce.Message}");
                flag = false;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Unknown exception, message: {ex.Message}");
                flag = false;
            }
            finally
            {
                _producer.Dispose();
            }
        }
    }
}
