import {EachMessagePayload, Kafka, KafkaConfig, ProducerRecord} from 'kafkajs';
import {SchemaRegistry} from "@kafkajs/confluent-schema-registry";
import {Purchase} from "../type";

const kafkaConfig: KafkaConfig = { brokers: ['localhost:19092'] }
const registry = new SchemaRegistry({
    host: "http://localhost:8081",
});
const kafka = new Kafka(kafkaConfig)

const consumerWithSchema = kafka.consumer({ groupId: 'test-group' })

consumerWithSchema.connect()
    .then(() => {
        consumerWithSchema.subscribe({ topic: 'purchases-with-schema', fromBeginning: true })
            .then(() => {
                console.log('Subscribed to topic')
                consumerWithSchema.run({
                    eachMessage: async ({ topic, partition, message }: EachMessagePayload) => {
                        console.log(message.value)
                        // @ts-ignore
                        const purchase: Purchase = await registry.decode(message.value)
                        console.log({
                            key: message.key?.toString(),
                            purchase: purchase,
                            partition,
                        })
                    },
                })
            })

    })
