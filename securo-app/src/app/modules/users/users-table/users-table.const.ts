import { defaultActions } from '../../../shared/components/table/table.const';
import { ColumnType, TableAction, TableColumn } from '../../../shared/components/table/table.interface';

export const userColumns: TableColumn[] = [
  {
    label: 'users.table.name',
    field: 'firstName',
    sortable: true,
    type: ColumnType.USER
  },
  {
    label: 'users.table.email',
    field: 'email',
    sortable: true
  }
];

export const userActions: TableAction[] = [
  ...defaultActions
];
