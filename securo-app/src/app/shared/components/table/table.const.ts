import { TableAction } from './table.interface';

export const defaultActions: TableAction[] = [
  {
    name: 'VIEW',
    icon: 'eye-outline',
    theme: 'default',
    custom: false
  },
  {
    name: 'REMOVE',
    icon: 'trash-outline',
    theme: 'default',
    custom: false
  }
];
