/**
 * Utilidad para descargar archivos PDF desde una cadena base64
 * 
 * @param {string} base64String - Cadena base64 del archivo PDF
 * @param {string} fileName - Nombre del archivo a descargar (ej: "reporte.pdf")
 * 
 * @example
 * downloadPdfFromBase64('JVBERi0xLjQKJeLjz9MK...', 'reporte.pdf');
 */
export const downloadPdfFromBase64 = (base64String, fileName = 'documento.pdf') => {
  try {
    // Verifica que se haya proporcionado una cadena base64
    if (!base64String) {
      throw new Error('No se proporcionó una cadena base64 válida');
    }

    // Elimina el prefijo "data:application/pdf;base64," si existe
    // Algunas APIs retornan el base64 con este prefijo, otras no
    const base64Data = base64String.includes(',') 
      ? base64String.split(',')[1] 
      : base64String;

    // Convierte la cadena base64 a un array de bytes (binary string)
    // atob() decodifica base64 a string binario
    const binaryString = atob(base64Data);

    // Crea un array de bytes desde el string binario
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }

    // Crea un Blob (Binary Large Object) con el tipo MIME de PDF
    // Un Blob es un objeto que representa datos binarios
    const blob = new Blob([bytes], { type: 'application/pdf' });

    // Crea una URL temporal para el blob
    // URL.createObjectURL() crea una URL que apunta al blob en memoria
    const url = URL.createObjectURL(blob);

    // Crea un elemento <a> temporal para descargar el archivo
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName; // Nombre del archivo que se descargará

    // Agrega el link al DOM temporalmente (necesario para algunos navegadores)
    document.body.appendChild(link);

    // Simula un clic en el link para iniciar la descarga
    link.click();

    // Limpia: remueve el link del DOM
    document.body.removeChild(link);

    // Libera la URL temporal para evitar memory leaks
    // Es importante hacer esto después de la descarga
    URL.revokeObjectURL(url);

  } catch (error) {
    // Si hay algún error, lo lanzamos para que el componente pueda manejarlo
    console.error('Error al descargar el PDF:', error);
    throw new Error('No se pudo descargar el archivo PDF: ' + error.message);
  }
};
