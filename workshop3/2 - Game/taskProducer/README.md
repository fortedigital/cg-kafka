# Task Producer Application

This application is a simple Kafka producer written in TypeScript. It reads tasks from a JSON file and sends them as messages to a Kafka topic.

## Prerequisites

Before you begin, ensure you have the following installed:

- Node.js and npm
- Kafka (ensure it's running)
- TypeScript

## Setup

1. Clone the repository and navigate to the `taskProducer` directory.

2. Install the dependencies:

```bash
npm install
```

## Configuration

The Kafka configuration is defined in the `ProduceQuestions.ts` file. You can modify the `brokers` array in the `kafkaConfig` object to point to your own Kafka cluster:

```typescript
const kafkaConfig: KafkaConfig = { brokers: ['<your-kafka-broker>'] }
```

## Usage

1. Start the producer:

```bash
npx ts-node ProduceQuestions.ts
```

This will read the tasks from the `team-registration.json` file and send them as messages to the `kafka-game` topic.
To read the tasks from a different file, modify the import statement in the `ProduceQuestions.ts` file:
```typescript
import data from './tasks/team-registration.json';
```

## Customization

You can customize the tasks by modifying the `team-registration.json` file. Each task should be an object with the following properties:

- `type`: The type of the task.
- `messageId`: A unique identifier for the task.
- `question`: The task question.
- `category`: The category of the task.
- `created`: The creation timestamp of the task.

Here is an example of a task:

```json
{
  "type": "QUESTION",
  "messageId": "41fe30bd-4050-45cb-80b2-cb2e82ec4b84",
  "question": "Registrér et nytt teamnavn og en hex-farge",
  "category": "team-registration",
  "created": "2022-11-07T14:53:27.581147"
}
```

## Conclusion

You have successfully set up and run the Task Producer Application. For more information and customization, refer to the Kafka and TypeScript documentation.
