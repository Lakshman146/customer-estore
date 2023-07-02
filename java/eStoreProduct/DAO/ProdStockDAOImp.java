package eStoreProduct.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eStoreProduct.model.ProdStock;
import eStoreProduct.model.ProductStockRowMapper;

@Component
public class ProdStockDAOImp implements ProdStockDAO {
	private static final String PD_STK_QUERY = "SELECT * FROM slam_productstock";
	private static final String SELECT_PRD_STK_QUERY = "SELECT * FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_PRICE_QUERY = "SELECT prod_price FROM slam_productstock WHERE prod_id = ?";
	private static final String PD_MRP_QUERY = "SELECT prod_mrp FROM slam_productstock WHERE prod_id = ?";

	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = 
			LoggerFactory.getLogger(ProdStockDAOImp.class);

	public ProdStockDAOImp(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	//method to get all the stock available
	public List<ProdStock> getAllProdStocks() {
		logger.info("eStoreProduct:DAO:ProductStockDAOImp:getting all the available products in the stock");
		return jdbcTemplate.query(PD_STK_QUERY, new ProductStockRowMapper());
	}

	@Override
	//method to get the stock of the product by id
	public ProdStock getProdStockById(int prodId) {
		logger.info("eStoreProduct:DAO:ProductStockDAOImp:getting the product stock by id");
		return jdbcTemplate.queryForObject(SELECT_PRD_STK_QUERY, new ProductStockRowMapper(), prodId);
	}

	@Override
	//method to get product price by id
	public double getProdPriceById(int prodId) {
		logger.info("eStoreProduct:DAO:ProductStockDAOImp:getting product price by id");
		return jdbcTemplate.queryForObject(PD_PRICE_QUERY, Double.class, prodId);
	}

	@Override
	//method to get the product mrp by id
	public double getProdMrpById(int prodId) {
		logger.info("eStoreProduct:DAO:ProductStockDAOImp:getting mrp of the product by id");
		return jdbcTemplate.queryForObject(PD_MRP_QUERY, Double.class, prodId);
	}

	@Override
	//method to get the product stock available by id
	public int getProdStock(int prod_id) {
		logger.info("eStoreProduct:DAO:ProductStockDAOImp:method to get product stock by its id");
		String Get_Prod_stck = "select prod_stock from slam_productStock where prod_id=?";
		return jdbcTemplate.queryForObject(Get_Prod_stck, Integer.class, prod_id);
	}
}
