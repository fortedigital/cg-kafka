using kafka_game.models;

namespace kafka_game;
public interface IKafkaProducer
{
    void Dispose();
    Task SendMessage(Answers answer);
}