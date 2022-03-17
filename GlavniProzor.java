package zadatak;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class GlavniProzor extends Frame
{
	private static final double d = 1;
	
	private double x, y;
	private int n;
	private short Z;
	private int Pn;
	
	private ArrayList<Integer> nList = new ArrayList<Integer>();
	private ArrayList<Double> PnList = new ArrayList<Double>();
	
	public GlavniProzor()
	{
		setBounds(200, 50, 700, 500);
		setTitle("Zadatak");

		populateWindow();
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		setVisible(true);
	}
	
	private void populateWindow()
	{
		Panel input = new Panel(new GridLayout(0, 2, 5, 5));
		Label text1 = new Label("Unesite n: ");
		text1.setAlignment(Label.CENTER);
		TextField nIn = new TextField();
		
		TextArea output = new TextArea();
		output.setVisible(true);
		output.setEditable(false);
		
		nIn.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER && nIn.getText().isEmpty() == false)
				{
					n = Integer.parseInt(nIn.getText());
					if(n <= 0)
						return;
					Pn = 0;
					nIn.setText("");
					output.setText("");
					calc(output);
					nIn.requestFocus();
				}
			}
		});
		
		Label text2 = new Label("Izlaz: ");
		Button submit = new Button("Potvrdi");
		Button graph = new Button("Iscrtaj grafik");
		Button listP = new Button("Tabelarni prikaz");
		
		Panel buttons = new Panel();
		buttons.add(graph);
		buttons.add(listP);
		
		submit.addActionListener((ae) -> {
			if(nIn.getText().isEmpty() == false)
			{
				n = Integer.parseInt(nIn.getText());
				if(n <= 0)
					return;
				Pn = 0;
				nIn.setText("");
				output.setText("");
				calc(output);
				nIn.requestFocus();
			}
		});
		
		graph.addActionListener((ae) -> {
			new GrafikProzor(nList, PnList);
		});
		
		listP.addActionListener((ae) -> {
			new TabelaProzor(nList, PnList);
		});
		
		input.add(text1);
		input.add(nIn);
		input.add(text2);
		input.add(submit);
		
		add(buttons, BorderLayout.SOUTH);
		add(input, BorderLayout.NORTH);
		add(output, BorderLayout.CENTER);
	}
	
	private void calc(TextArea l)
	{
		StringBuilder sb = new StringBuilder();
		double duzina1, duzina2, duzina3;
		for(int i = 0; i < n; i++)
		{
			x = Math.random();
			y = Math.random();
			
			if(y < x)
			{
				double temp = x;
				x = y;
				y = temp;
			}
			
			duzina1 = x;
			duzina2 = y - x;
			duzina3 = d - y;
			
			if(duzina1 + duzina2 > duzina3 && duzina1 + duzina3 > duzina2 && duzina2 + duzina3 > duzina1)
			{
				Z = 1;
				Pn++;
			}
			else
				Z = 0;
			
			sb.append(Z).append(" ");
		}
		
		PnList.add((double)Pn/n);
		nList.add(n);
		//System.out.println("\n" + Pn + "\n" + n);
		
		l.setText(sb.toString());
	}
	
	public static void main(String[] args)
	{
		new GlavniProzor();
	}

}

