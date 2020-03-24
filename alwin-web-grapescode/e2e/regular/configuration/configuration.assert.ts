import {ConfigurationPage} from '../../util/po/configuration.po';

export class ConfigurationAssert {

  constructor(private page: ConfigurationPage) {
  }

  expectIssueTypeConfigurations() {
    this.expectIssueTypeConfiguration(1, 'Windykacja telefoniczna sekcja 1', '14', 'A', 'Tak', '1', '-100,00', 'Administrator systemu\nWindykator' +
      ' telefoniczny\nMenedżer windykacji telefonicznej\nWindykator telefoniczny sekcja 1');
    this.expectIssueTypeConfiguration(2, 'Windykacja telefoniczna sekcja 2', '17', 'A', 'Tak', '16', '-100,00',
      'Administrator systemu\nWindykator telefoniczny sekcja 2');
    this.expectIssueTypeConfiguration(3, 'Windykacja bezpośrednia', '13', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(4, 'Windykacja prawna - pozew o wydanie przedmiotu', '14', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(5, 'Transport przedmiotu', '15', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(6, 'Realizacja zabezpieczenia', '16', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(7, 'Windykacja prawna - pozew o zapłatę', '17', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(8, 'Restrukturyzacja', '18', 'A', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(9, 'Windykacja telefoniczna sekcja 1', '9', 'B', 'Tak', '1', '-100,00', 'Administrator systemu\nWindykator' +
      ' telefoniczny\nMenedżer windykacji telefonicznej\nWindykator telefoniczny sekcja 1');
    this.expectIssueTypeConfiguration(10, 'Windykacja telefoniczna sekcja 2', '12', 'B', 'Tak', '11', '-100,00',
      'Administrator systemu\nWindykator telefoniczny sekcja 2');
    this.expectIssueTypeConfiguration(11, 'Windykacja bezpośrednia', '23', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(12, 'Windykacja prawna - pozew o wydanie przedmiotu', '24', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(13, 'Transport przedmiotu', '25', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(14, 'Realizacja zabezpieczenia', '26', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(15, 'Windykacja prawna - pozew o zapłatę', '27', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
    this.expectIssueTypeConfiguration(16, 'Restrukturyzacja', '28', 'B', 'Nie', '5', '-100,00', 'Administrator systemu');
  }

  expectIssueTypeConfiguration(number: number, issueType: string, duration: string, segment: string, createAutomatically: string,
                               dpdStart: string, minBalanceStart: string, operatorTypes: string) {
    const row = this.page.getConfigurationsTableRow('configuration-table', number);
    expect(row.get(0).getText()).toBe(issueType);
    expect(row.get(1).getText()).toBe(duration);
    expect(row.get(2).getText()).toBe(segment);
    expect(row.get(3).getText()).toBe(createAutomatically);
    expect(row.get(4).getText()).toBe(dpdStart);
    expect(row.get(5).getText()).toBe(minBalanceStart);
    expect(row.get(6).getText()).toBe(operatorTypes);
  }

  expectActivityTypeConfigurations() {
    this.expectActivityTypeConfiguration(1, 'Telefon wychodzący', 'tak', 'min. 0 / max. b/d', 'nie', 'tak', 'tak', 'tak',
      'Czy pozostawiono' + ' wiadomość\nDługość rozmowy\nKomentarz\nNr tel.\nRozmówca', 'Czy pozostawiono wiadomość\nDługość rozmowy\nKomentarz\nNr tel.');
    this.expectActivityTypeConfiguration(2, 'Wezwanie do zapłaty (podstawowe)', 'nie', 'min. 50 / max. 203.33', 'tak', 'nie', 'tak', 'nie', 'Termin zapłaty', '');
    this.expectActivityTypeConfiguration(3, 'Wezwanie do zapłaty (ostateczne)', 'nie', 'min. 150.33 / max. 100.99', 'tak', 'nie', 'tak', 'nie', 'Termin zapłaty', '');
    this.expectActivityTypeConfiguration(4, 'Wiadomość SMS wychodząca', 'tak', 'min. 0 / max. b/d', 'tak', 'nie', 'tak', 'tak', 'Nr tel.\nTreść wiadomości', '');
    this.expectActivityTypeConfiguration(5, 'Telefon przychodzący', 'nie', 'min. 0 / max. b/d', 'nie', 'tak', 'tak', 'tak', 'Długość rozmowy\nNr tel.\nRozmówca', '');
    this.expectActivityTypeConfiguration(6, 'Komentarz', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
    this.expectActivityTypeConfiguration(7, 'Wizyta terenowa', 'tak', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', 'Komentarz', 'Komentarz');
    this.expectActivityTypeConfiguration(8, 'Wypowiedzenie warunkowe umowy', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
    this.expectActivityTypeConfiguration(9, 'Wezwanie do zwrotu przedmiotu', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
    this.expectActivityTypeConfiguration(10, 'Podejrzenie fraudu', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
    this.expectActivityTypeConfiguration(11, 'Odbiór przedmiotu leasingu', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
    this.expectActivityTypeConfiguration(12, 'Potwierdzenie wpłaty klienta', 'nie', 'min. 0 / max. b/d', 'nie', 'nie', 'nie', 'nie', '', '');
  }

  expectActivityTypeConfiguration(number: number, name: string, canBePlanned: string, charge: string, mayBeAutomated: string, mayHaveDeclaration: string,
                                  specific: string, customerContact: string, plannedActivityDetailProperty: string, activityDetailProperty: string) {
    const row = this.page.getConfigurationsTableRow('activity-types-table', number);
    expect(row.get(0).getText()).toBe(name);
    expect(row.get(1).getText()).toBe(canBePlanned);
    expect(row.get(2).getText()).toBe(charge);
    expect(row.get(3).getText()).toBe(mayBeAutomated);
    expect(row.get(4).getText()).toBe(mayHaveDeclaration);
    expect(row.get(5).getText()).toBe(specific);
    expect(row.get(6).getText()).toBe(customerContact);
    expect(row.get(7).getText()).toBe(plannedActivityDetailProperty);
    expect(row.get(8).getText()).toBe(activityDetailProperty);
  }

  expectDefaultActivityConfigurationsForFirstTab() {
    this.expectDefaultActivityConfiguration(1, 'Telefon wychodzący', '1', '1', '01.07.2017', '13.12.2012');
    this.expectDefaultActivityConfiguration(2, 'Wezwanie do zapłaty (podstawowe)', '3', '2', '10.07.2017', '15.12.2012');
    this.expectDefaultActivityConfiguration(3, 'Wezwanie do zapłaty (ostateczne)', '7', '2', '04.07.2017', '19.12.2012');
    this.expectDefaultActivityConfiguration(4, 'Telefon wychodzący', '10', '1', '03.07.2017', '22.12.2012');
  }

  expectDefaultActivityConfigurationsForSecondTab() {
    this.expectDefaultActivityConfiguration(1, 'Telefon wychodzący', '1', '1', '05.07.2017', '13.12.2012');
    this.expectDefaultActivityConfiguration(2, 'Wezwanie do zapłaty (ostateczne)', '7', '1', '06.07.2017', '19.12.2012');
  }

  expectDefaultActivityConfiguration(number: number, activityType: string, defaultDay: string, version: string, creatingDate: string,
                                     defaultExecutionDate: string) {
    const row = this.page.getConfigurationsTableRow('default-activities-table', number);
    expect(row.get(0).getText()).toBe(activityType);
    expect(row.get(1).getText()).toBe(defaultDay);
    expect(row.get(2).getText()).toBe(version);
    expect(row.get(3).getText()).toBe(creatingDate);
    expect(row.get(4).getText()).toBe(defaultExecutionDate);
  }
}
