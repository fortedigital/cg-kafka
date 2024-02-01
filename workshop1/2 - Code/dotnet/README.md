# GO BACK TO [Workshop 1/Code/README](../README.md)
Dette er en guide for å lage consumer og producer men dette er ikke oppgaven.
Denne kan brukes hvis dere sitter fast.

# Kafka .net Producer and Consumer

In this workshop we will get a simple first look at Kafka and how to create messages and consume those messages.

## Requirements

To follow this tutorial you need to have an understanding of programming with c# and dotnet. You can complete the workshop using Visual Studio Code, Visual Studio is not a necessity.

The code assumes you have Kafka running, if you don't you can quickly get started by running the the Docker Kafka compose file found [here(docker-compose.yml)](../../docker-compose.yml).


## Step-by-step

To get started with Kafka you will create a producer and consumer that connects to a running Kafka service. You will se how the producer create entries, and how the consumers can retrieve them.

The producer will add messages mimicking a purchases log. And the consumer will read the log.

### Step 1. Create the project

### Visual Studio

Create a new console application called *consumer*, make sure to keep the solution outside the project, as you will create multiple projects. The solution can be called something like *Kafka-introduction* 

Then create a second project inside the solution called *producer*.

### VS Code

For VS Code, create a new dir called something like *Kafka-introduction*, then create two subdirectories,*Producer* and *Consumer*.

#### Producer 

Inside the producer folder create a **consumer.csproj** file and a **Program.cs** file.

**producer.csproj**
```csproj
<Project Sdk="Microsoft.NET.Sdk">

  <PropertyGroup>
    <OutputType>Exe</OutputType>
    <TargetFramework>net6.0</TargetFramework>
    <ImplicitUsings>enable</ImplicitUsings>
    <Nullable>enable</Nullable>
  </PropertyGroup>

</Project>
```

#### Consumer

Then within the consumer folder create a **producer.csproj** file and a **Program.cs** file.

**consumer.csproj**
```csproj
<Project Sdk="Microsoft.NET.Sdk">

  <PropertyGroup>
    <OutputType>Exe</OutputType>
    <TargetFramework>net6.0</TargetFramework>
    <ImplicitUsings>enable</ImplicitUsings>
    <Nullable>enable</Nullable>
  </PropertyGroup>

</Project>
```

### Step 2. Nuget and settings

Add the following nuget packages to both projects (or later versions):

```csproj
<ItemGroup>
  <PackageReference Include="Confluent.Kafka" Version="2.3.0" />
  <PackageReference Include="Microsoft.Extensions.Configuration" Version="8.0.0" />
  <PackageReference Include="Microsoft.Extensions.Configuration.Json" Version="8.0.0" />
</ItemGroup>
```

#### Appsettings

Then add an **appsettings.json** file in both projects, and add the following json:

```json
{
  "bootstrap.servers": "localhost:19092"
}
```

You can later add more settings to make the setup process easier.

Add the following code to read the **appsetting.json** file and create a IConfiguration config in **program.cs** for both projects.

```c#
IConfiguration config = new ConfigurationBuilder()
        .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
        .AddJsonFile("appsettings.json", false, true)
        .Build();
```

### Step 3. Producer

First, after the config, create a string with the topic your producer will add messages to in Kafka. Then create a list of users, and a list of items.

```c#
const string TOPIC = "purchases";

string[] users = { "bob", "brian", "frank" };
string[] items = { "book", "alarm clock", "t-shirts", "gift card", "batteries" };
```

Add the following code to initialize the Kafka producer, and send messages with random users and items. 

```c#
using (var producer = new ProducerBuilder<string, string>(
    config.AsEnumerable()).Build())
{
    var numProduced = 0;
    Random rnd = new Random();
    const int NUMMESSAGES = 10;
    for (int i = 0; i < NUMMESSAGES; i++)
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
                    Console.WriteLine($"Produced event to topic {TOPIC}: key = {user,-10} value = {item}");
                    numProduced += 1;
                }
            });
        Thread.Sleep(2000);
    }

    producer.Flush(TimeSpan.FromSeconds(10));
    producer.Dispose();
    Console.WriteLine($"{numProduced} messages were produced to topic {TOPIC}");

}
```

### Step 4. Consumer

First, after initializing the config you need to add two more items to the config,

```c#
config["group.id"] = "test-consumer";
config["auto.offset.reset"] = "earliest";
```

These can also be added in the **appsettings.json** file.

Then add a string for the topic your consumer will consume from. 

```c#
const string TOPIC = "purchases";
```

The consumer runs in an infinite while loop to always be ready to receive messages. But the consumer needs to close the connection to Kafka in a graceful way to prevent errors. To do this you first create a CancellationTokenSource, and wrap the while loop in a try catch.

```c#
CancellationTokenSource cts = new CancellationTokenSource();
Console.CancelKeyPress += (_, e) =>
{
    e.Cancel = true; // prevent the process from terminating.
    cts.Cancel();
};
```

Add the following code to create the Kafka Consumer and continuously consume messages.

```c#
using (var consumer = new ConsumerBuilder<string, string>(
        configuration.AsEnumerable()).Build())
    {
        // have the consumer subscribe to our topic
        consumer.Subscribe(TOPIC);

        // To gracefully handle possible errors or closing the connection we wrap the listening loop in a trycatch
        try
        {
            while (true)
            {
                // consume and display the message
                var cr = consumer.Consume(cts.Token);
                Console.WriteLine($"Consumed event from topic {TOPIC}: key = {cr.Message.Key,-10} value = {cr.Message.Value}");
            }
        }
        catch (OperationCanceledException)
        {
            // Ctrl-C was pressed.
        }
        finally
        {
            // gracefully close the connection then dispose of the object.
            consumer.Close();
            consumer.Dispose();
        }
    }
```
