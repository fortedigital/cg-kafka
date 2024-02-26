using kafka_wikimedia_stream;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

HostApplicationBuilder builder = new HostApplicationBuilder(args);

builder.Services.AddSingleton<IWikimediaSocket, WikimediaSocket>();
builder.Services.AddSingleton<IKafkaProducer, KafkaProducer>();
using IHost host = builder.Build();

// Get the startpoint service
var running = host.Services.GetService<IWikimediaSocket>();

 await running.Stream("https://stream.wikimedia.org/v2/stream/recentchange");