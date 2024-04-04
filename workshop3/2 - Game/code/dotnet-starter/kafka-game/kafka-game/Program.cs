using kafka_game;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

HostApplicationBuilder builder = new HostApplicationBuilder(args);

builder.Services.AddSingleton<IKafkaProducer, KafkaProducer>();
builder.Services.AddSingleton<IGameApp, GameApp>();

using IHost host = builder.Build();

var game = host.Services.GetService<IGameApp>();

await game.Start();