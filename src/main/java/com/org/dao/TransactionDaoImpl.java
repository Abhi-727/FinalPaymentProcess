package com.org.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.org.constant.Constants;
import com.org.constant.ErrorCodeEnum;
import com.org.constant.TransactionStausEnum;
import com.org.dto.TransactionDto;
import com.org.entity.TransactionEntity;
import com.org.exceptionHandling.ProcessServiceException;

@Component
public class TransactionDaoImpl implements ITransactionDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private ModelMapper mapper;

	@Override
	public TransactionDto createTransaction(TransactionDto txnDTO) {

		System.out.println("*****namedParameterJdbcTemplate : " + namedParameterJdbcTemplate);

		System.out.println("TransactionDaoImpl.createTransaction() : received txnDto : " + txnDTO);

		TransactionEntity txnEntity = mapper.map(txnDTO, TransactionEntity.class);
		System.out.println("converted into entity : " + txnEntity);

		String sql = "INSERT INTO payments.Transaction (userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, amount, currency, merchantTransactionReference, txnReference, providerReference, providerCode, providerMessage, creationDate, updatedDate, retryCount) "
				+ "VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, :merchantTransactionReference, :txnReference, :providerReference, :providerCode, :providerMessage, CURRENT_TIMESTAMP(2), CURRENT_TIMESTAMP(2), :retryCount)";
		// for Auto increment
		KeyHolder keyholder = new GeneratedKeyHolder();

		try {
			int insertedRowCount = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(txnEntity),
					keyholder, new String[] { "id" }); 
			int transactionId = keyholder.getKey().intValue();

			txnDTO.setId(transactionId); /// ???

			System.out.println("Inserted value in Db count insertedRowCount : " + insertedRowCount
					+ " | transactionId : " + transactionId);

			return txnDTO;

		} catch (DuplicateKeyException ex) {
			System.out.println("Unable to process,Duplicate txnRef is given");
			System.out.println("Caught DuplicateKeyException: " + ex.getClass().getName());
			System.out.println("Error message: " + ex.getMessage());
			ex.printStackTrace();
			throw new ProcessServiceException(ErrorCodeEnum.DUPLICATE_TXN_REFERENCE.getErrorCode(),
					ErrorCodeEnum.DUPLICATE_TXN_REFERENCE.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public TransactionDto getTransactionById(int transactionId) {

		TransactionEntity txnEntity = null;
		try {
			String sql = "Select * from Transaction where id=:id";
			txnEntity = namedParameterJdbcTemplate.queryForObject(sql,
					new BeanPropertySqlParameterSource(TransactionEntity.builder().id((int) transactionId).build()),
					new BeanPropertyRowMapper<>(TransactionEntity.class));
			System.out.println(" :: transaction Details from DB  = " + txnEntity);

			TransactionDto txnDto = mapper.map(txnEntity, TransactionDto.class);

			System.out.println("Returning txnEntity:" + txnDto);
			return txnDto;

		} catch (Exception e) {
			System.out.println("unable to get transaction Details " + e);
			throw e;
		}

	}

	@Override
	public boolean updateTxnStatus(TransactionDto txnDto) {

		TransactionEntity txnEntity = mapper.map(txnDto, TransactionEntity.class);
		System.out.println("Converted Entity:" + txnEntity);
		String sql = "Update Transaction SET txnStatusId=:txnStatusId, providerReference=:providerReference WHERE id=:id";
		int updateCount = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(txnEntity));
		return (updateCount == 1);

	}

	@Override
	public TransactionDto getTransactionByProviderReference(String id) {

		System.out.println("TransactionDaoImpl.getTransactionByProviderReference()" + "|id:" + id);

		TransactionEntity txnEntity = null;

		String sql = "Select * from Transaction where providerReference=:providerReference";

		try {
			SqlParameterSource params = new MapSqlParameterSource().addValue("providerReference", id);

			txnEntity = namedParameterJdbcTemplate.queryForObject(sql, params,
					new BeanPropertyRowMapper<>(TransactionEntity.class));
			System.out.println(" :: transaction Details from DB  = " + txnEntity);

			TransactionDto txnDto = mapper.map(txnEntity, TransactionDto.class);

			System.out.println("Returning txnEntity:" + txnDto);
			return txnDto;

		} catch (Exception e) {
			System.out.println("unable to getTransactionByProviderReference " + e);

			throw e;
		}
	}
	
	@Override
	public List<TransactionDto> fetchTransactionsForReconcilation() {
		System.out.println(" :: fetching Transaction Details  for retry :: ");

		String sql = "Select * from Transaction where retryCount < :maxRetryCount and txnStatusId = :reconsileStatusId";
		
		List<TransactionEntity> transaction = new ArrayList<>();
		try {
			
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("maxRetryCount", Constants.MAX_RETRY_COUNT)
					.addValue("reconsileStatusId", TransactionStausEnum.PENDING.getId());
			
			transaction = namedParameterJdbcTemplate.query(sql, params,
					new BeanPropertyRowMapper<>(TransactionEntity.class));
			
			System.out.println(" :: transaction Details from DB  = " + transaction);
		} catch (Exception e) {
			System.out.println("unable to get transaction Details " + e);
			e.printStackTrace();
		}

		return convertToDTOList(transaction);
	}

	private List<TransactionDto> convertToDTOList(List<TransactionEntity> transactionEntities) {
		return transactionEntities.stream()
				.map(entity -> mapper.map(entity, TransactionDto.class))
				.collect(Collectors.toList());
	}


}
