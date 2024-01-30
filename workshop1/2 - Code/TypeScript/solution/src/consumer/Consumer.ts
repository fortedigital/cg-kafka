import {EachMessagePayload, Kafka, KafkaConfig, ProducerRecord} from 'kafkajs';

const kafkaConfig: KafkaConfig = { brokers: ['localhost:19092'] }
const kafka = new Kafka(kafkaConfig)

const consumer = kafka.consumer({ groupId: 'test-group' })

consumer.connect()
    .then(() => {
        consumer.subscribe({ topic: 'purchases', fromBeginning: true })
            .then(() => {
                console.log('Subscribed to topic')
                consumer.run({
                    eachMessage: async ({ topic, partition, message }: EachMessagePayload) => {
                        console.log({
                            key: message.key?.toString(),
                            value: message.value?.toString(),
                            partition,
                        })
                    },
                })
            })

    })
