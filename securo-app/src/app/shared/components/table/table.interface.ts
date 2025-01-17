export interface TableColumn {
  label: string;
  field: string;
  sortable?: boolean;
  type?: ColumnType;
}

export enum ColumnType {
  BOOLEAN = 'BOOLEAN',
  DATE = 'DATE',
  USER = 'USER',
  BADGE = 'BADGE'
}

export interface TableAction {
  name: 'VIEW' | string;
  icon: string;
  data?: any;
}
