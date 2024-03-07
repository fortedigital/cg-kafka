import {Kafka, KafkaConfig, ProducerRecord, RecordMetadata} from 'kafkajs';
import {SchemaRegistry, SchemaType} from "@kafkajs/confluent-schema-registry";
import {Purchase} from "../type";

const kafkaConfig: KafkaConfig = { brokers: ['localhost:19092']}
const registry = new SchemaRegistry({
    host: "http://localhost:8081",
});

const kafka = new Kafka(kafkaConfig)

const producer = kafka.producer()

const users = ["Daud", "Thomas", "Ola", "Erol", "Ludvig" ];
const items = [ "Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug" ];

const schema = `
  {
    "definitions" : {
      "record:examples.Person" : {
        "type" : "object",
        "required" : [ "user", "item" ],
        "additionalProperties" : false,
        "properties" : {
          "user" : {
            "type" : "string"
          },
          "item" : {
            "type" : "string"
          }
        }
      }
    },
    "$ref" : "#/definitions/record:examples.Person"
  }
`
producer.connect()
    .then(async () => {
        console.log('Connected to Kafka')
        // @ts-ignore
        const { id } = await registry.register({ type: SchemaType.JSON, schema }, {subject: "purchases-with-schema"})
        for (let i = 0; i < 15; i++) {
            let user = users[Math.floor(Math.random() * users.length)];
            let item = items[Math.floor(Math.random() * items.length)];
            let purchase: Purchase = {
                user: user,
                item: item
            }
            let message = {
                key: user,
                value: await registry.encode(id, purchase)
            };
            let value: ProducerRecord = {
                topic: 'purchases-with-schema',
                messages: [
                    message,
                ],
            };
            producer.send(value).then((value: RecordMetadata[]) => {
                console.log(`Message key: ${message.key} with value: ${message.value} sent to partition: ${value[0].partition} with offset: ${value[0].offset}`)
            })
        }
        producer.disconnect()
    })
