import {Activity} from '../../activity/activity';

export const TEST_ACTIVITY = JSON.parse(`
  {
	"id": 100,
	"issueId": 1,
	"operator": {
		"id": 1,
		"firstName": "Adam",
		"lastName": "Mickiewicz",
		"role": {
			"name": "ADMIN"
		}
	},
	"activityType": {
		"id": 1,
		"name": "Telefonwychodzący",
		"canBePlanned": true,
		"mayBeAutomated": false,
		"specific": true,
		"mayHaveDeclaration": true,
		"chargeMin": 0.00,
		"chargeMax": null,
		"activityDetailProperties": [{
				"id": 2,
				"name": "Czypozostawionowiadomość",
				"type": "java.lang.Boolean"
			}, {
				"id": 1,
				"name": "Nrtel.",
				"type": "java.lang.String"
			}, {
				"id": 4,
				"name": "Czyrozmowazosobądecyzyjną",
				"type": "java.lang.Boolean"
			}, {
				"id": 3,
				"name": "Długośćrozmowy",
				"type": "java.lang.Integer"
			}
		]
	},
	"activityDate": 1499551200000,
	"plannedDate": 1499637600000,
	"creationDate": 1499205600000,
	"state": {
		"key": "PLANNED",
		"label": "Zaplanowana"
	},
	"remark": "Bardzogrzecznyklient,polecam!",
	"charge": 10.32,
	"invoiceId": "fv1a/2017",
	"activityDetails": [{
			"id": 100,
			"detailProperty": {
				"id": 1,
				"name": "Nrtel.",
				"type": "java.lang.String"
			},
			"value": "01242-234-234-234"
		}, {
			"id": 101,
			"detailProperty": {
				"id": 2,
				"name": "Czypozostawionowiadomość",
				"type": "java.lang.Boolean"
			},
			"value": "FALSE"
		}, {
			"id": 102,
			"detailProperty": {
				"id": 5,
				"name": "Termin zapłaty",
				"type": "java.util.Date"
			},
			"value": 1506862800000
		}
	],
	"declarations": [{
			"id": 123,
			"declarationDate": 1506862800000,
			"declaredPaymentDate": 1501592400000,
			"declaredPaymentAmount": 43323.12,
			"cashPaid": 2345.34,
			"monitored": true
		}, {
			"id": 124,
			"declarationDate": 1501592400000,
			"declaredPaymentDate": 1498914000000,
			"declaredPaymentAmount": 121243.12,
			"cashPaid": 5432.34,
			"monitored": false
		}
	]
}
`) as Activity;
