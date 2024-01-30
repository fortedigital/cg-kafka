import {Kafka, KafkaConfig, ProducerRecord} from 'kafkajs';

const kafkaConfig: KafkaConfig = { brokers: ['localhost:19092'] }
const kafka = new Kafka(kafkaConfig)

const producer = kafka.producer()
producer.connect()
    .then(() => {
        console.log('Connected to Kafka')
        let value: ProducerRecord = {
            topic: 'purchases',
            messages: [
                { value: 'Hello KafkaJS user!' },
            ],
        };
        producer.send(value)
    })
