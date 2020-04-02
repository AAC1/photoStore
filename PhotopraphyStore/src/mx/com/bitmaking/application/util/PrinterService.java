package mx.com.bitmaking.application.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javafx.collections.ObservableList;
import mx.com.bitmaking.application.dto.CostProductsDTO;
import mx.com.bitmaking.application.dto.UserSessionDTO;

public class PrinterService implements Printable{

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /*
         * User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        /* Now we perform our rendering */

        graphics.setFont(new Font("Roman", 0, 8));
        graphics.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
	}
	
	public static void printTicket(String printerName, String text)throws Exception {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        
        PrintService service = findPrintService(printerName, printService);
        if(service ==null){
        	throw new Exception("La impresora no se encuentra configurada.");
        }
        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);
            
            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
        	System.out.println("serviceName"+service.getName());
            if (printerName.toUpperCase().equals(service.getName().toUpperCase())) {
                return service;
            }
        }

        return null;
    }
    
    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }

	public static String ticketLayout(int nCharacters, ObservableList<CostProductsDTO> listProds,UserSessionDTO instance) {
		
		StringBuilder layout = new StringBuilder();
		layout.append(nCharacter(nCharacters, "MACROFOTO DIGITAL S.A. DE C.V.", true, false));
		layout.append(nCharacter(nCharacters, instance.getDirSucursal(), true, false));
		layout.append(nCharacter(nCharacters, "Raz\u00F3n Social:"+instance.getRazon_social(), true, false));
		layout.append(nCharacter(nCharacters, "Sucursal: "+instance.getPrefijo() +" "+instance.getSucursal(), false, false));
		layout.append(nCharacter(nCharacters, "", false, false));
		layout.append(nCharacter(nCharacters, "Productos:", false, false));
		int  nlastLine = 0, aux = 0;
		BigDecimal totalCost = new BigDecimal(0);
		String txt = "", costo = "", preTxt = "", lastLine = "";

		for (CostProductsDTO el : listProds) {
			txt = el.getProducto() + " x" + el.getCantidad();
			preTxt =nCharacter(nCharacters, txt, false, true);
			totalCost=totalCost.add(el.getCosto());
			costo = GeneralMethods.formatCurrentNumber(String.valueOf(el.getCosto()));
			//System.out.println("preTxt:"+preTxt);
			nlastLine = preTxt.lastIndexOf('\n');
		//	System.out.println("nlastLine:"+nlastLine);
			if (nlastLine > 0) {
				lastLine = preTxt.substring(nlastLine+1, preTxt.length());
		//		System.out.println("lastLine:"+lastLine.length() +" value:"+lastLine);
				if ((preTxt.length()-1 + costo.length()) < nCharacters) {
					aux = nCharacters - (preTxt.length()-1 + costo.length());
					preTxt = preTxt.substring(0,preTxt.length()-1);
				}
				else if ((lastLine.length() + costo.length()) < nCharacters) {
					aux = nCharacters - (lastLine.length() + costo.length());
					
				} else {
					aux = nCharacters - (costo.length());
					preTxt+="\n";
				//	preTxt =preTxt.substring(0, preTxt.length())+ nSpace(aux) + costo;
				}
				preTxt += GeneralMethods.nSpace(aux) + costo;
				layout.append(preTxt+ "\n");
			}
			preTxt += GeneralMethods.nSpace(aux) + costo;
			// layout.append(nCharacter(nCharacters,"Productos:",false,true));

		}
		layout.append(nCharacter(nCharacters, "", false, false));
		String formTotal = GeneralMethods.formatCurrentNumber(String.valueOf(totalCost));
		aux = nCharacters - ("Total:".length() + formTotal.length());
		layout.append("Total:").append(GeneralMethods.nSpace(aux)).append(formTotal);
		layout.append(nCharacter(nCharacters, "", false, false));
		layout.append(nCharacter(nCharacters, "Te atendi\u00F3: "+instance.getInstance().getNombre(), false, true));
		layout.append(nCharacter(nCharacters, "", false, false));
		layout.append(nCharacter(nCharacters, "¡GRACIAS POR SU PREFERENCIA!", true, false));
		layout.append(nCharacter(nCharacters, "TELEFONO:"+instance.getTelSucursal(), true, false));
		layout.append("\n\n\n\n\n\n\n\n\n\n");
		return layout.toString();
	}

	public static String nCharacter(int ln, String text, boolean center, boolean txtProduct) {
		StringBuilder resp = new StringBuilder();
		int textLength = text.length();
		
		int naux = (ln) - textLength;
		int lineAux = 0;
		int lastIdx = 0;
		int npadding = 0;
		if (textLength >= ln) {

			while (lineAux < textLength) {
				lastIdx = lineAux + ln - 1;
				
				if (lastIdx <= textLength) {
					resp.append(text.substring(lineAux, (lastIdx)));
					resp.append("\n");
				} else {
					if (center) {
						npadding = ((ln) - (textLength - lineAux)) / 2;

						resp.append(GeneralMethods.nSpace(npadding));
					}
					resp.append(text.substring(lineAux, textLength));

				}
				lineAux += (ln - 1);
			}
			if (!txtProduct) {
				
				resp.append("\n");
			}

		} else if (center) {
			npadding = naux / 2;
			resp.append(GeneralMethods.nSpace(npadding)).append(text);
			if (!txtProduct) {
				resp.append("\n");
			}
		} else {
			resp.append(text).append("\n");
		}

		return resp.toString();
	}
}
