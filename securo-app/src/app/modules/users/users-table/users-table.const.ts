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
  },
  {
    label: 'users.table.country',
    field: 'country',
    sortable: true,
    type: ColumnType.BADGE,
  },
  {
    label: 'users.table.city',
    field: 'city',
    sortable: true
  },
  {
    label: 'users.table.active',
    field: 'active',
    sortable: true,
    type: ColumnType.BOOLEAN
  }
];

export const userActions: TableAction[] = [
  ...defaultActions
];
