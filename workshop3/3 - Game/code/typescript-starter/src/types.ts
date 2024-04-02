export enum MessageType {
  Question = "QUESTION",
  Answer = "ANSWER",
  Assessment = "ASSESSMENT",
}

export enum AssessmentStatus {
  Success = "SUCCESS",
  Failure = "FAILURE",
}

export interface LeesahMessage {
  messageId: string;
  type: MessageType;
  category: string;
  created: string;
}

export interface Question extends LeesahMessage {
  category: string;
  question: string;
}

export interface Answer extends LeesahMessage {
  questionId: string;
  teamName: string;
  answer: string;
}

export interface Assessment extends LeesahMessage {
  questionId: string;
  answerId: string;
  type: MessageType;
  teamName: string;
  status: AssessmentStatus;
  sign: string;
}
