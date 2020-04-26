/**
 * 
 */
package mx.com.bitmaking.application.abstractdao;

import mx.com.bitmaking.application.entity.Store_cargo_abono;
import mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO;

/**
 * @author albcervantes
 *
 */
public abstract 
class AbstractStoreCargoAbonoDAO implements IStoreCargoAbonoDAO {

	/* (non-Javadoc)
	 * @see mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO#saveCargoAbono(mx.com.bitmaking.application.entity.Store_cargo_abono)
	 */
	@Override
	public void saveCargoAbono(Store_cargo_abono row) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see mx.com.bitmaking.application.idao.IStoreCargoAbonoDAO#getCargoAbonoByDateSuc()
	 */
	@Override
	public Store_cargo_abono getCargoAbonoByDateSuc() {
		// TODO Auto-generated method stub
		return null;
	}

}
