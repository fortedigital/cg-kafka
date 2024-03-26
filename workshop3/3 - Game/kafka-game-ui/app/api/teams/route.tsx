import { NextRequest, NextResponse } from "next/server";
import Fetcher from "@/lib/Fetcher";
import { Team } from "@/lib/model";
import { handleResponse } from "@/lib/utils";

export async function GET(req: NextRequest): Promise<NextResponse> {
  const url = `http://localhost:8080/teams`;
  const headers = {
    "Content-Type": "application/json",
  };
  return Fetcher<Team[]>({
    url: url,
    method: "GET",
    headers: headers,
    body: undefined,
  })
    .then(handleResponse)
    .then((data) => {
      if (data === undefined || data.length === 0) {
        return NextResponse.json([{ key: "error", value: "No support org" }], {
          status: 500,
        });
      } else {
        return NextResponse.json(data);
      }
    })
    .catch((error) => {
      return NextResponse.json([{ key: "error", value: error }], {
        status: 500,
      });
    });
}
