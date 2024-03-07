using Newtonsoft.Json;

namespace cg_kafka_wikimedia_stream;

public class WikimediaSocket : IWikimediaSocket
{
    private HttpClient _client;
    private IKafkaProducer _kafkaProducer;

    public WikimediaSocket(IKafkaProducer kafkaProducer)
    {
        _client = new HttpClient();
        _client.Timeout = TimeSpan.FromSeconds(10);
        _kafkaProducer = kafkaProducer;
    }

    public async Task Stream(string url)
    {
        CancellationTokenSource cts = new CancellationTokenSource();
        Console.CancelKeyPress += (_, e) =>
        {
            e.Cancel = true;
            cts.Cancel();
        };
        bool flag = true;

        while (flag)
        {
            try
            {
                using (var streamReader = new StreamReader(await _client.GetStreamAsync(url)))
                {
                    while (!streamReader.EndOfStream && !cts.Token.IsCancellationRequested)
                    {
                        var message = await streamReader.ReadLineAsync();

                        if (string.IsNullOrEmpty(message)) continue;

                        if (message.Equals(":ok"))
                        {
                            Console.WriteLine($"Connected to the stream: {message}");
                        }
                        else if (message.Contains("data: "))
                        {
                            var messageList = message.Split("data: ");

                            var converted = JsonConvert.DeserializeObject<RecentChangeData>(messageList[1]);

                            Console.ForegroundColor = ConsoleColor.Green;
                            Console.WriteLine(converted.ToString());
                            Console.ForegroundColor = ConsoleColor.White;

                            await _kafkaProducer.SendMessage(converted);
                        }

                    }
                }

                if (cts.Token.IsCancellationRequested)
                {
                    throw new OperationCanceledException();
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
                _kafkaProducer.Dispose();
            }
        }
    }
}