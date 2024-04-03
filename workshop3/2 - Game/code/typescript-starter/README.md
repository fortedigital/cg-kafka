# leesah-game-starter-ts

Muligheten for å spille lessah-game i Node.js og TypeScript!

## For lokal utvikling av starteren

For å spinne opp docker-compose:

Sette opp host:

```bash
export HOST_IP=$(ifconfig | grep -E "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d: | head -n1)
```

Kjør docker compose

```bash
docker compose up -d
```
