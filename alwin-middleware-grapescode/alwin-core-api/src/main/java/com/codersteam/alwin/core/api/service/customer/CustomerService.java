package com.codersteam.alwin.core.api.service.customer;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceDto;
import com.codersteam.alwin.core.api.model.customer.ContractOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.CustomerDto;
import com.codersteam.alwin.core.api.model.customer.CustomerOutOfServiceDto;
import com.codersteam.alwin.core.api.model.customer.CustomerOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionContractOutOfServiceInputDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionCustomerOutOfServiceDto;
import com.codersteam.alwin.core.api.model.customer.FormalDebtCollectionCustomerOutOfServiceInputDto;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Serwis odpowiedzialny za pobieranie kontrahentów
 *
 * @author Adam Stepnowski
 */
@Local
public interface CustomerService {

    /**
     * Pobierenie klienta po identyfikatorze z zewnętrznego systemu
     *
     * @param extCustomerId - identyfikator klienta z zewnętrznego systemu
     * @return klient
     */
    Optional<CustomerDto> findCustomerByExtId(Long extCustomerId);

    /**
     * Sprawdzenie, czy klient nie jest wyłączony z obsługi na dany dzień w windykacji nieformalnej
     *
     * @param customerId    - id klienta
     * @param operationDate - data dla której jest sprawdzany warunek
     * @return true jeżeli klient jest wyłaczony z obsługi, false w przeciwnym razie
     */
    boolean isCustomerOutOfService(Long customerId, Date operationDate);

    /**
     * Sprawdzenie, czy klient nie jest wyłączony z obsługi na dany dzień w windykacji formalnej
     *
     * @param extCompanyId            - id klienta
     * @param operationDate           - data dla której jest sprawdzany warunek
     * @param demandForPaymentTypeKey - typ wezwania
     * @return true jeżeli klient jest wyłaczony z obsługi, false w przeciwnym razie
     */
    boolean isFormalDebtCollectionCustomerOutOfService(Long extCompanyId, Date operationDate, DemandForPaymentTypeKey demandForPaymentTypeKey);

    /**
     * Pobiera listę aktywnych zablokowań kontraktów dla podanego klienta w windykacji nieformalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista blokad kontraktów
     */
    List<ContractOutOfServiceDto> findActiveContractsOutOfService(final Long extCompanyId);

    /**
     * Pobiera listę aktywnych zablokowań kontraktów dla podanego klienta w windykacji formalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista blokad kontraktów
     */
    List<FormalDebtCollectionContractOutOfServiceDto> findActiveFormalDebtCollectionContractsOutOfService(final Long extCompanyId);

    /**
     * Pobiera listę numerów aktualnie zablokowanych umów podanego klienta
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista numerów aktualnie zablokowanych umów
     */
    Set<String> findActiveContractOutOfServiceNumbers(final Long extCompanyId);

    /**
     * Pobiera listę wszystkich zablokowań kontraktów dla podanego klienta w windykacji nieformalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista blokad kontraktów
     */
    List<ContractOutOfServiceDto> findAllContractsOutOfService(final Long extCompanyId);

    /**
     * Pobiera listę wszystkich zablokowań kontraktów dla podanego klienta w windykacji formalnej
     *
     * @param extCompanyId - identyfikator klienta z zewnętrznego systemu
     * @return lista blokad kontraktów
     */
    List<FormalDebtCollectionContractOutOfServiceDto> findAllFormalDebtCollectionContractsOutOfService(final Long extCompanyId);

    /**
     * Zwraca listę aktualnych blokad klienta w windykacji nieformalnej
     *
     * @param extCompanyId - identyfikator firmy z zewnętrznego systemu
     * @param active       - czy pobierać blokady tylko aktywne
     * @return lista blokad klienta
     */
    List<CustomerOutOfServiceDto> findCustomersOutOfService(final Long extCompanyId, final Boolean active);

    /**
     * Zwraca listę aktualnych blokad klienta w windykacji formalnej
     *
     * @param extCompanyId - identyfikator firmy z zewnętrznego systemu
     * @param active       - czy pobierać blokady tylko aktywne
     * @return lista blokad klienta
     */
    List<FormalDebtCollectionCustomerOutOfServiceDto> findFormalDebtCollectionCustomersOutOfService(final Long extCompanyId, final Boolean active);

    /**
     * Blokuje klienta według przesłanych parametrów w windykacji nieformalnej
     *
     * @param customer           - klient z parametrami do zablokowania
     * @param extCompanyId       - identyfikator blokowanego klienta
     * @param blockingOperatorId - identyfikator operatora, który blokuje klienta
     */
    void blockCustomer(final CustomerOutOfServiceInputDto customer, final long extCompanyId, final Long blockingOperatorId);

    /**
     * Blokuje klienta według przesłanych parametrów w windykacji formalnej
     *
     * @param customer           - klient z parametrami do zablokowania
     * @param extCompanyId       - identyfikator blokowanego klienta
     * @param blockingOperatorId - identyfikator operatora, który blokuje klienta
     */
    void blockFormalDebtCollectionCustomer(final FormalDebtCollectionCustomerOutOfServiceInputDto customer, final long extCompanyId, final Long blockingOperatorId);

    /**
     * Blokuje kontrakt klienta w windykacji nieformalnej według przesłanych parametrów.
     * Tworzy klienta w systemie, jeżeli nie został on jeszcze zarejestrowany.
     *
     * @param contract           - kontrakt z parametrami do zablokowania
     * @param extCompanyId       - identyfikator blokowanego klienta
     * @param contractNo         - numer blokowanego kontraktu
     * @param blockingOperatorId - identyfikator operatora, który blokuje klienta
     */
    void blockCustomerContract(final ContractOutOfServiceInputDto contract, final long extCompanyId, final String contractNo, final Long blockingOperatorId);

    /**
     * Blokuje kontrakt klienta w windykacji formalnej według przesłanych parametrów.
     * Tworzy klienta w systemie, jeżeli nie został on jeszcze zarejestrowany.
     *
     * @param contract           - kontrakt z parametrami do zablokowania
     * @param extCompanyId       - identyfikator blokowanego klienta
     * @param contractNo         - numer blokowanego kontraktu
     * @param blockingOperatorId - identyfikator operatora, który blokuje klienta
     */
    void blockFormalDebtCollectionContract(final FormalDebtCollectionContractOutOfServiceInputDto contract, final long extCompanyId, final String contractNo,
                                           final Long blockingOperatorId);


    /**
     * Aktualizuje blokadę klienta według przesłanych parametrów w windykacji nieformalnej
     *
     * @param customer               - klient z parametrami do zablokowania
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @param blockingOperatorId     - identyfikator operatora, który aktualizuje blokadę klienta
     */
    void updateCustomerOutOfService(final CustomerOutOfServiceInputDto customer, final long customerOutOfServiceId, final Long blockingOperatorId);

    /**
     * Aktualizuje blokadę klienta według przesłanych parametrów w windykacji formalnej
     *
     * @param customer               - klient z parametrami do zablokowania
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @param blockingOperatorId     - identyfikator operatora, który aktualizuje blokadę klienta
     */
    void updateFormalDebtCollectionCustomerOutOfService(final FormalDebtCollectionCustomerOutOfServiceInputDto customer, final long customerOutOfServiceId,
                                                        final Long blockingOperatorId);


    /**
     * Aktualizuje blokadę umowy klienta według przesłanych parametrów w windykacji nieformalnej
     *
     * @param contract               - kontrakt z parametrami do zablokowania
     * @param customerOutOfServiceId - identyfikator blokady umowy klienta
     * @param blockingOperatorId     - identyfikator operatora, który blokuje umowę klienta
     */
    void updateCustomerContractOutOfService(final ContractOutOfServiceInputDto contract, final Long customerOutOfServiceId, final Long blockingOperatorId);

    /**
     * Aktualizuje blokadę umowy klienta według przesłanych parametrów w windykacji formalnej
     *
     * @param contract               - kontrakt z parametrami do zablokowania
     * @param customerOutOfServiceId - identyfikator blokady umowy klienta
     * @param blockingOperatorId     - identyfikator operatora, który blokuje umowę klienta
     */
    void updateFormalDebtCollectionCustomerContractOutOfService(final FormalDebtCollectionContractOutOfServiceInputDto contract, final Long customerOutOfServiceId, final Long blockingOperatorId);

    /**
     * Sprawdza czy blokada klienta o podanym identyfikatorze istnieje w windykacji nieformalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @return true, jeżeli blokada klienta istnieje, false w przeciwnym wypadku
     */
    boolean checkIfCustomerOutOfServiceExists(final long customerOutOfServiceId);

    /**
     * Sprawdza czy blokada klienta o podanym identyfikatorze istnieje w windykacji formalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady klienta
     * @return true, jeżeli blokada klienta istnieje, false w przeciwnym wypadku
     */
    boolean checkIfFormalDebtCollectionCustomerOutOfServiceExists(final long customerOutOfServiceId);

    /**
     * Sprawdza czy blokada umowy klienta o podanym identyfikatorze istnieje w windykacji nieformalnej
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     * @param extCompanyId           - identyfikator klienta z zewnętrznego systemu
     * @return true, jeżeli blokada umowy klienta istnieje, false w przeciwnym wypadku
     */
    boolean checkIfContractOutOfServiceExists(long contractOutOfServiceId, final long extCompanyId);

    /**
     * Sprawdza czy blokada umowy klienta o podanym identyfikatorze istnieje w windykacji formalnej
     *
     * @param contractOutOfServiceId - identyfikator blokady umowy klienta
     * @param extCompanyId           - identyfikator klienta z zewnętrznego systemu
     * @return true, jeżeli blokada umowy klienta istnieje, false w przeciwnym wypadku
     */
    boolean checkIfFormalDebtCollectionContractOutOfServiceExists(long contractOutOfServiceId, final long extCompanyId);

    /**
     * Usuwa blokadę klienta o podanym identyfikatorze w windykacji nieformalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady
     */
    void deleteCustomerOutOfService(long customerOutOfServiceId);

    /**
     * Usuwa blokadę klienta o podanym identyfikatorze w windykacji formalnej
     *
     * @param customerOutOfServiceId - identyfikator blokady
     */
    void deleteFormalDebtCollectionCustomerOutOfService(long customerOutOfServiceId);

    /**
     * Usuwa blokadę umowy klienta o podanym identyfikatorze w windykacji nieformalnej
     *
     * @param contractOutOfServiceId - identyfikator blokady
     */
    void deleteContractOutOfService(long contractOutOfServiceId);

    /**
     * Usuwa blokadę umowy klienta o podanym identyfikatorze w windykacji formalnej
     *
     * @param contractOutOfServiceId - identyfikator blokady
     */
    void deleteFormalDebtCollectionContractOutOfService(long contractOutOfServiceId);

    /**
     * Modyfikacja opiekuna klienta.
     * Usuwanie następuje, gdy przekazany identyifikator opiekuna jest pusty
     *
     * @param customerId       - identyfikator klienta
     * @param accountManagerId - identyfikator opiekuna
     */
    void updateCustomerAccountManager(final long customerId, final Long accountManagerId);

    /**
     * Sprawdza czy istenieje klient o podanym identyfikatorze
     *
     * @param customerId - identyfikator klienta
     * @return true - jeżeli klient istnieje, false - w przeciwnym przypadku
     */
    boolean checkIfCustomerExists(final long customerId);
}
