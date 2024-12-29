export interface TableColumn {
  label: string;
  field: string;
  sortable?: boolean;
  type?: ColumnType;
}

export enum ColumnType {
  DATE = 'DATE',
  USER = 'USER'
}

export interface TableAction {
  name: 'EDIT' | 'VIEW' | string;
  icon: string;
  theme: 'default' | 'primary' | 'secondary';
  data?: any;
  custom: boolean;
}
