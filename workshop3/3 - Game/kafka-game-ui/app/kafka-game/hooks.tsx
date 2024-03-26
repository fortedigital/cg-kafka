"use client";

import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { handleResponse } from "@/lib/utils";
import { Team } from "@/lib/model";

type TeamState = {
  teams: Team[];
};

export const KafkaGameHooks = (leaderboard: string) => {
  const [teamState, setTeamState] = useState<TeamState>({ teams: [] });
  useEffect(() => {
    fetchTeams(teamState, setTeamState);

    // Set an interval to call fetchTeams every 5 seconds
    const intervalId = setInterval(() => {
      fetchTeams(teamState, setTeamState);
    }, 5000); // 5000 milliseconds = 5 seconds

    // Clear the interval when the component is unmounted
    return () => clearInterval(intervalId);
  }, []);

  return {
    teamState,
  };
};

const fetchTeams = async (
  teamState: TeamState,
  setTeamState: Dispatch<SetStateAction<TeamState>>,
) => {
  try {
    const teamsData = await fetch(`/api/teams`).then(handleResponse);
    setTeamState({
      ...teamState,
      teams: [...teamsData],
    });
  } catch (error) {
    console.error("Error fetching data:", { error });
  }
};
