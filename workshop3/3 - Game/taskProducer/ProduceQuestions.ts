import {Kafka, KafkaConfig, ProducerRecord} from 'kafkajs';
import data from './tasks/team-registration.json';
const kafkaConfig: KafkaConfig = { brokers: ['kafka.daud.dev:19092'] }
const kafka = new Kafka(kafkaConfig)

const producer = kafka.producer()
type Question = {
    messageId: string;
    type: string;
    created: string;
    category: string;
    question: string;

}

producer.connect()
    .then(() => {
        console.log('Connected to Kafka')
        data.forEach(d => {
            producer.send({
                topic: "kafka-game",
                messages: [
                    {
                        value: JSON.stringify({
                            messageId: d.messageId,
                            type: d.type,
                            created: d.created,
                            category: d.category,
                            question: d.question,
                        }),
                    },
                ],
            });
        })
    })
