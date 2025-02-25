export interface Environment {
  id: string;

  name: string;
  code: string;

  type: EnvironmentType;

  description?: string;

  index: number;

  createdAt: Date;
  updatedAt?: Date;
}

export enum EnvironmentType {
  DEV = 'DEV',
  TEST = 'TEST',
  STAGE = 'STAGE',
  PROD = 'PROD',
}

export interface UpdateEnvironmentsOrderDto {
  type: EnvironmentType;
  environments: Environment[];
}
