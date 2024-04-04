import { Consumer, Kafka, Producer } from 'kafkajs';
import { v4 as uuidv4 } from 'uuid';
import { MessageType, Question } from "../types";

const QUIZ_TOPIC = process.env.QUIZ_TOPIC || 'kafka-game'
const BROKER_URL = process.env.BOOTSTRAP_SERVERS || 'kafka.daud.dev:19092'
const CONSUMER_GROUP_ID = process.env.CONSUMER_GROUP_ID || '<create your own>'

let producer: Producer;
let teamName: string;

export const loadKafka = async (
  team: string,
): Promise<{ consumer: Consumer }> => {
  if (!BROKER_URL)
    throw new Error(`Broker url er feil! Broker url: ${BROKER_URL}`);

  teamName = team;

  const kafka = new Kafka({
    clientId: `kafka-game-${team}`,
    brokers: [BROKER_URL],
  });

  const consumer = kafka.consumer({ groupId: CONSUMER_GROUP_ID });
  await consumer.connect();
  await consumer.subscribe({ topic: QUIZ_TOPIC, fromBeginning: true });

  producer = kafka.producer();
  await producer.connect();

  return { consumer };
};

export const answerQuestion = async ({
  question,
  answer,
}: {
  question: Question;
  answer: string;
}) => {
  await producer.send({
    topic: QUIZ_TOPIC,
    messages: [
      {
        value: JSON.stringify({
          messageId: uuidv4(),
          type: MessageType.Answer,
          created: new Date().toISOString(),
          questionId: question.messageId,
          category: question.category,
          teamName,
          answer,
        }),
      },
    ],
  });
};
