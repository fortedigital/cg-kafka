namespace cg_kafka_wikimedia_stream;

public interface IKafkaProducer
{
    void Dispose();
    Task SendMessage(RecentChangeData recentChange);
}