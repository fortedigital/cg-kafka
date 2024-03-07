import {Kafka, KafkaConfig, ProducerRecord, RecordMetadata} from 'kafkajs';

const kafkaConfig: KafkaConfig = { brokers: ['localhost:19092'] }
const kafka = new Kafka(kafkaConfig)

const producer = kafka.producer()

const users = ["Daud", "Thomas", "Ola", "Erol", "Ludvig" ];
const items = [ "Book", "Alarm clock", "T-shirts", "Gift card", "Batteries", "Soda", "Coffee mug" ];

producer.connect()
    .then(() => {
        console.log('Connected to Kafka')
        for (let i = 0; i < 15; i++) {
            let message = {
                key: users[Math.floor(Math.random() * users.length)],
                value: items[Math.floor(Math.random() * items.length)]
            };
            let value: ProducerRecord = {
                topic: 'purchases',
                messages: [
                    message,
                ],
            };
            producer.send(value).then((value: RecordMetadata[] ) => {
                console.log(`Message key: ${message.key} with value: ${message.value} sent to partition: ${value[0].partition} with offset: ${value[0].offset}`)
            })
        }
        producer.disconnect()
    })
