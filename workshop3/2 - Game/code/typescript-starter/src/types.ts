export enum MessageType {
  Question = "QUESTION",
  Answer = "ANSWER",
  Assessment = "ASSESSMENT",
}

export enum AssessmentStatus {
  Success = "SUCCESS",
  Failure = "FAILURE",
}

export interface KafkaMessage {
  messageId: string;
  type: MessageType;
  category: string;
  created: string;
}

export interface Question extends KafkaMessage {
  category: string;
  question: string;
}

export interface Answer extends KafkaMessage {
  questionId: string;
  teamName: string;
  answer: string;
}

export interface Assessment extends KafkaMessage {
  questionId: string;
  answerId: string;
  type: MessageType;
  teamName: string;
  status: AssessmentStatus;
  sign: string;
}
