"use client";

import { KafkaGameHooks } from "@/app/kafka-game/hooks";
import { Team } from "@/lib/model";
import "./page.module.css";

const TeamComponent = (props: Team) => {
  // sort answers by position
  const answers = props.answers.sort((a, b) => a.position - b.position);

  return (
    <div className="container border p-4 rounded-3 m-2">
      <div className="row">
        <div className="col">
          <div className="row d-flex align-items-center justify-content-center">
            <div
              className="col-1 rounded-circle"
              style={{
                width: "3vw",
                height: "3vw",
                backgroundColor: props.hexColor,
              }}
            ></div>
            <h4 className="col mr-3">{props.name}</h4>
          </div>
          <p>Score: {props.score}</p>
        </div>
      </div>
      <div className="row">
        {answers.map((answer, index) => (
          <div key={index} className="col">
            <h6>{answer.category}</h6>
            <p>{answer.totalAnswers}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default function Home() {
  const { teamState } = KafkaGameHooks("leaderboard");
  // sort teams by score
  const teams = teamState.teams.sort((a, b) => b.score - a.score);
  return (
    <div className="container-fluid">
      <div className="row flex justify-content-center align-content-center text-center">
        <h1>Leaderboards</h1>
        <p>Life is a stream of questions</p>
      </div>
      <div className="row">
        {teams.map((team) => (
          <div key={team.id} className="col-6 col-xl-3">
            <TeamComponent {...team} />
          </div>
        ))}
      </div>
    </div>
  );
}
