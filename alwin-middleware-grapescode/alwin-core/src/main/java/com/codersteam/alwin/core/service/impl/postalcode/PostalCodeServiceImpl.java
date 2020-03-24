package com.codersteam.alwin.core.service.impl.postalcode;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeInputDto;
import com.codersteam.alwin.core.api.postalcode.PostalCodeService;
import com.codersteam.alwin.core.service.mapper.AlwinMapper;
import com.codersteam.alwin.db.dao.PostalCodeDao;
import com.codersteam.alwin.jpa.PostalCode;
import com.codersteam.alwin.jpa.operator.Operator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Michal Horowic
 */
@Stateless
@SuppressWarnings({"EjbClassBasicInspection"})
public class PostalCodeServiceImpl implements PostalCodeService {

    private final PostalCodeDao dao;
    private final AlwinMapper mapper;

    @Inject
    public PostalCodeServiceImpl(final PostalCodeDao dao, final AlwinMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Page<PostalCodeDto> findPostalCodesByMask(final String mask) {
        final long count = dao.findPostalCodesByMaskCount(mask);
        final List<PostalCode> postalCodes = dao.findPostalCodesByMask(mask);
        return new Page<>(mapper.mapAsList(postalCodes, PostalCodeDto.class), count);
    }

    @Override
    public Optional<OperatorDto> findOperatorForPostalCode(final String postalCode) {
        return dao.findOperatorForPostalCode(postalCode).map(this::mapOperator);
    }

    private OperatorDto mapOperator(final Operator operator) {
        return mapper.map(operator, OperatorDto.class);
    }

    @Override
    public boolean checkIfPostalCodeExists(final long postalCodeId) {
        return dao.exists(postalCodeId);
    }

    @Override
    public boolean checkIfPostalCodeMaskExists(final String mask) {
        return dao.checkIfPostalCodeMaskExists(mask);
    }

    @Override
    public void updatePostalCode(final long postalCodeId, final PostalCodeInputDto postalCode) {
        final PostalCode existingPostalCode = mapper.map(postalCode, PostalCode.class);
        existingPostalCode.setId(postalCodeId);
        dao.update(existingPostalCode);
    }

    @Override
    public void createPostalCode(final PostalCodeInputDto postalCode) {
        final PostalCode newPostalCode = mapper.map(postalCode, PostalCode.class);
        dao.create(newPostalCode);
    }

    @Override
    public void deletePostalCode(final long postalCodeId) {
        dao.delete(postalCodeId);
    }
}
