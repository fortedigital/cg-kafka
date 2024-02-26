namespace kafka_wikimedia_stream;

public interface IWikimediaSocket
{
    Task Stream(string url);
}