import {Operator} from '../../operator/operator';

export const FIRST_OPERATOR: Operator = JSON.parse(
  `{
    "id": 2,
    "active": true,
    "permission": null,
    "parentOperator": {
      "id": 4,
      "active": true,
      "permission": null,
      "parentOperator": null,
      "user": {
        "id": 4,
        "firstName": "Czesław",
        "lastName": "Miłosz",
        "type": null
      },
      "type": {
        "id": 3,
        "typeName": "PHONE_DEBT_COLLECTOR_MANAGER",
        "typeLabel": "Menedżer windykacji telefonicznej"
      }
    },
    "user": {
      "id": 2,
      "firstName": "Juliusz",
      "lastName": "Słowacki",
      "type": null
    },
    "type": {
      "id": 2,
      "typeName": "PHONE_DEBT_COLLECTOR",
      "typeLabel": "Windykator telefoniczny"
    }
  }`
);
