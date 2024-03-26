import { NextResponse } from "next/server";

export const handleResponse = (res: NextResponse<any> | Response) => {
  if (isStatusOk(res)) {
    return res.json();
  }
  throw Error(res.statusText);
};

export const isStatusOk = (res: Response) => {
  return res.status > 199 && res.status < 301;
};
