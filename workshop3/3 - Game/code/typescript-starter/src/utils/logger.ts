export const logJSON = (json: any) => {
  console.log(JSON.stringify(json, null, 2));
};

export const logYellow = (message: string) => {
  console.log(`\x1b[33m ${message} \x1b[0m`);
};
