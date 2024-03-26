import {KafkaStreams, KafkaStreamsConfig} from "kafka-streams";
import * as most from "most";

const config: KafkaStreamsConfig = {
    noptions  : {
        "metadata.broker.list": "localhost:19092",
        "group.id": "wordcount-application",
        "client.id": "wordcount-application",
    },
    tconf: {
        "auto.offset.reset": "earliest",
    },
}

const factory = new KafkaStreams(config);

let kStream = factory.getKStream("ts-word-count-input");

kStream
    // 1 - stream from Kafka
    .from("my-input-topic")
    // 2 - map values to lowercase
    .map((kafkaMessage) => {
        const value = kafkaMessage.value.toString("utf8");
        const elements = value.toLowerCase().split(" ");
        return {
            key: elements[0],
        };
    })
    // 3 - flatmap values split by space
    .concatMap((kafkaMessage) => {
        const value = kafkaMessage.value.toString("utf8");
        const elements = value.toLowerCase().split(" ");
        return most.from(elements);
    })
    // 4 - select key to apply a key (we discard the old key)
    .map((kafkaMessage) => {
        const value = kafkaMessage.value.toString("utf8");
        return {
            value: value,
        };
    })
    .map((kafkaMessage) => {
        const value = kafkaMessage.value.toString("utf8");
        const elements = value.toLowerCase().split(" ");
        return {
            someField: elements[0],
        };
    })
    .countByKey("someField", "count")
    .filter(kv => kv.count >= 3)
    .map(kv => kv.someField + " " + kv.count)
    .tap(kv => console.log(kv))
    .to("ts-word-count-output");

const inputStream = factory.getKStream();
inputStream.to("ts-word-count-input");

const produceInterval = setInterval(() => {
    inputStream.writeToStream("kah vow");
}, 100);

Promise.all([
    kStream.start(),
    inputStream.start()
]).then(() => {
    console.log("started..");
    // produce & consume for 5 seconds
    setTimeout(() => {
        clearInterval(produceInterval);
        factory.closeAll();
        console.log("stopped..");
    }, 5000);
});
