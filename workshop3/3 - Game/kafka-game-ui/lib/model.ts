export type Team = {
  id: string;
  name: string;
  score: number;
  hexColor: string;
  answers: Answer[];
};

export type Answer = {
  category: string;
  totalScore: number;
  totalAnswers: number;
};
