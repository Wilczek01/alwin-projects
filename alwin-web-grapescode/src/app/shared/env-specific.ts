export class EnvSpecific {
  url: string;
  production: boolean;

  constructor(url: string, production: boolean) {
    this.url = url;
    this.production = production;
  }
}
